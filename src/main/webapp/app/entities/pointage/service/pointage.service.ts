import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { map, Observable } from 'rxjs';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { DATE_FORMAT } from 'app/config/input.constants';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IPointage, NewPointage } from '../pointage.model';

export type PartialUpdatePointage = Partial<IPointage> & Pick<IPointage, 'id'>;

type RestOf<T extends IPointage | NewPointage> = Omit<T, 'datuDuJour'> & {
  datuDuJour?: string | null;
};

export type RestPointage = RestOf<IPointage>;

export type NewRestPointage = RestOf<NewPointage>;

export type PartialUpdateRestPointage = RestOf<PartialUpdatePointage>;

export type EntityResponseType = HttpResponse<IPointage>;
export type EntityArrayResponseType = HttpResponse<IPointage[]>;

@Injectable({ providedIn: 'root' })
export class PointageService {
  protected http = inject(HttpClient);
  protected applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/pointages');

  create(pointage: NewPointage): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pointage);
    return this.http
      .post<RestPointage>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(pointage: IPointage): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pointage);
    return this.http
      .put<RestPointage>(`${this.resourceUrl}/${this.getPointageIdentifier(pointage)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(pointage: PartialUpdatePointage): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(pointage);
    return this.http
      .patch<RestPointage>(`${this.resourceUrl}/${this.getPointageIdentifier(pointage)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestPointage>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestPointage[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getPointageIdentifier(pointage: Pick<IPointage, 'id'>): number {
    return pointage.id;
  }

  comparePointage(o1: Pick<IPointage, 'id'> | null, o2: Pick<IPointage, 'id'> | null): boolean {
    return o1 && o2 ? this.getPointageIdentifier(o1) === this.getPointageIdentifier(o2) : o1 === o2;
  }

  addPointageToCollectionIfMissing<Type extends Pick<IPointage, 'id'>>(
    pointageCollection: Type[],
    ...pointagesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const pointages: Type[] = pointagesToCheck.filter(isPresent);
    if (pointages.length > 0) {
      const pointageCollectionIdentifiers = pointageCollection.map(pointageItem => this.getPointageIdentifier(pointageItem));
      const pointagesToAdd = pointages.filter(pointageItem => {
        const pointageIdentifier = this.getPointageIdentifier(pointageItem);
        if (pointageCollectionIdentifiers.includes(pointageIdentifier)) {
          return false;
        }
        pointageCollectionIdentifiers.push(pointageIdentifier);
        return true;
      });
      return [...pointagesToAdd, ...pointageCollection];
    }
    return pointageCollection;
  }

  protected convertDateFromClient<T extends IPointage | NewPointage | PartialUpdatePointage>(pointage: T): RestOf<T> {
    return {
      ...pointage,
      datuDuJour: pointage.datuDuJour?.format(DATE_FORMAT) ?? null,
    };
  }

  protected convertDateFromServer(restPointage: RestPointage): IPointage {
    return {
      ...restPointage,
      datuDuJour: restPointage.datuDuJour ? dayjs(restPointage.datuDuJour) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestPointage>): HttpResponse<IPointage> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestPointage[]>): HttpResponse<IPointage[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
