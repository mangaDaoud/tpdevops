import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IPointage, NewPointage } from '../pointage.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IPointage for edit and NewPointageFormGroupInput for create.
 */
type PointageFormGroupInput = IPointage | PartialWithRequiredKeyOf<NewPointage>;

type PointageFormDefaults = Pick<NewPointage, 'id'>;

type PointageFormGroupContent = {
  id: FormControl<IPointage['id'] | NewPointage['id']>;
  datuDuJour: FormControl<IPointage['datuDuJour']>;
  heureArrive: FormControl<IPointage['heureArrive']>;
  heureDepart: FormControl<IPointage['heureDepart']>;
  employee: FormControl<IPointage['employee']>;
};

export type PointageFormGroup = FormGroup<PointageFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class PointageFormService {
  createPointageFormGroup(pointage: PointageFormGroupInput = { id: null }): PointageFormGroup {
    const pointageRawValue = {
      ...this.getFormDefaults(),
      ...pointage,
    };
    return new FormGroup<PointageFormGroupContent>({
      id: new FormControl(
        { value: pointageRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      datuDuJour: new FormControl(pointageRawValue.datuDuJour),
      heureArrive: new FormControl(pointageRawValue.heureArrive),
      heureDepart: new FormControl(pointageRawValue.heureDepart),
      employee: new FormControl(pointageRawValue.employee),
    });
  }

  getPointage(form: PointageFormGroup): IPointage | NewPointage {
    return form.getRawValue() as IPointage | NewPointage;
  }

  resetForm(form: PointageFormGroup, pointage: PointageFormGroupInput): void {
    const pointageRawValue = { ...this.getFormDefaults(), ...pointage };
    form.reset(
      {
        ...pointageRawValue,
        id: { value: pointageRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): PointageFormDefaults {
    return {
      id: null,
    };
  }
}
