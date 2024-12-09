import { TestBed } from '@angular/core/testing';

import { sampleWithRequiredData, sampleWithNewData } from '../pointage.test-samples';

import { PointageFormService } from './pointage-form.service';

describe('Pointage Form Service', () => {
  let service: PointageFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PointageFormService);
  });

  describe('Service methods', () => {
    describe('createPointageFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPointageFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            datuDuJour: expect.any(Object),
            heureArrive: expect.any(Object),
            heureDepart: expect.any(Object),
            employee: expect.any(Object),
          }),
        );
      });

      it('passing IPointage should create a new form with FormGroup', () => {
        const formGroup = service.createPointageFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            datuDuJour: expect.any(Object),
            heureArrive: expect.any(Object),
            heureDepart: expect.any(Object),
            employee: expect.any(Object),
          }),
        );
      });
    });

    describe('getPointage', () => {
      it('should return NewPointage for default Pointage initial value', () => {
        const formGroup = service.createPointageFormGroup(sampleWithNewData);

        const pointage = service.getPointage(formGroup) as any;

        expect(pointage).toMatchObject(sampleWithNewData);
      });

      it('should return NewPointage for empty Pointage initial value', () => {
        const formGroup = service.createPointageFormGroup();

        const pointage = service.getPointage(formGroup) as any;

        expect(pointage).toMatchObject({});
      });

      it('should return IPointage', () => {
        const formGroup = service.createPointageFormGroup(sampleWithRequiredData);

        const pointage = service.getPointage(formGroup) as any;

        expect(pointage).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPointage should not enable id FormControl', () => {
        const formGroup = service.createPointageFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPointage should disable id FormControl', () => {
        const formGroup = service.createPointageFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
