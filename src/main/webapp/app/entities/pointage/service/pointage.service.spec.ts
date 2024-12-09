import { TestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IPointage } from '../pointage.model';
import { sampleWithRequiredData, sampleWithNewData, sampleWithPartialData, sampleWithFullData } from '../pointage.test-samples';

import { PointageService, RestPointage } from './pointage.service';

const requireRestSample: RestPointage = {
  ...sampleWithRequiredData,
  datuDuJour: sampleWithRequiredData.datuDuJour?.format(DATE_FORMAT),
};

describe('Pointage Service', () => {
  let service: PointageService;
  let httpMock: HttpTestingController;
  let expectedResult: IPointage | IPointage[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
    });
    expectedResult = null;
    service = TestBed.inject(PointageService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a Pointage', () => {
      const pointage = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(pointage).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Pointage', () => {
      const pointage = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(pointage).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Pointage', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Pointage', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Pointage', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addPointageToCollectionIfMissing', () => {
      it('should add a Pointage to an empty array', () => {
        const pointage: IPointage = sampleWithRequiredData;
        expectedResult = service.addPointageToCollectionIfMissing([], pointage);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(pointage);
      });

      it('should not add a Pointage to an array that contains it', () => {
        const pointage: IPointage = sampleWithRequiredData;
        const pointageCollection: IPointage[] = [
          {
            ...pointage,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPointageToCollectionIfMissing(pointageCollection, pointage);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Pointage to an array that doesn't contain it", () => {
        const pointage: IPointage = sampleWithRequiredData;
        const pointageCollection: IPointage[] = [sampleWithPartialData];
        expectedResult = service.addPointageToCollectionIfMissing(pointageCollection, pointage);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(pointage);
      });

      it('should add only unique Pointage to an array', () => {
        const pointageArray: IPointage[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const pointageCollection: IPointage[] = [sampleWithRequiredData];
        expectedResult = service.addPointageToCollectionIfMissing(pointageCollection, ...pointageArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const pointage: IPointage = sampleWithRequiredData;
        const pointage2: IPointage = sampleWithPartialData;
        expectedResult = service.addPointageToCollectionIfMissing([], pointage, pointage2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(pointage);
        expect(expectedResult).toContain(pointage2);
      });

      it('should accept null and undefined values', () => {
        const pointage: IPointage = sampleWithRequiredData;
        expectedResult = service.addPointageToCollectionIfMissing([], null, pointage, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(pointage);
      });

      it('should return initial array if no Pointage is added', () => {
        const pointageCollection: IPointage[] = [sampleWithRequiredData];
        expectedResult = service.addPointageToCollectionIfMissing(pointageCollection, undefined, null);
        expect(expectedResult).toEqual(pointageCollection);
      });
    });

    describe('comparePointage', () => {
      it('Should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePointage(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('Should return false if one entity is null', () => {
        const entity1 = { id: 123 };
        const entity2 = null;

        const compareResult1 = service.comparePointage(entity1, entity2);
        const compareResult2 = service.comparePointage(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey differs', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 456 };

        const compareResult1 = service.comparePointage(entity1, entity2);
        const compareResult2 = service.comparePointage(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('Should return false if primaryKey matches', () => {
        const entity1 = { id: 123 };
        const entity2 = { id: 123 };

        const compareResult1 = service.comparePointage(entity1, entity2);
        const compareResult2 = service.comparePointage(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
