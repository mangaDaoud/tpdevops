import { inject } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRouteSnapshot, Router } from '@angular/router';
import { of, EMPTY, Observable } from 'rxjs';
import { mergeMap } from 'rxjs/operators';

import { IPointage } from '../pointage.model';
import { PointageService } from '../service/pointage.service';

const pointageResolve = (route: ActivatedRouteSnapshot): Observable<null | IPointage> => {
  const id = route.params['id'];
  if (id) {
    return inject(PointageService)
      .find(id)
      .pipe(
        mergeMap((pointage: HttpResponse<IPointage>) => {
          if (pointage.body) {
            return of(pointage.body);
          } else {
            inject(Router).navigate(['404']);
            return EMPTY;
          }
        }),
      );
  }
  return of(null);
};

export default pointageResolve;
