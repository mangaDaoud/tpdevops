import { Injectable } from '@angular/core';
import { FormGroup, FormControl, Validators } from '@angular/forms';

import { IEmployee, NewEmployee } from '../employee.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IEmployee for edit and NewEmployeeFormGroupInput for create.
 */
type EmployeeFormGroupInput = IEmployee | PartialWithRequiredKeyOf<NewEmployee>;

type EmployeeFormDefaults = Pick<NewEmployee, 'id'>;

type EmployeeFormGroupContent = {
  id: FormControl<IEmployee['id'] | NewEmployee['id']>;
  matricule: FormControl<IEmployee['matricule']>;
  prenom: FormControl<IEmployee['prenom']>;
  nom: FormControl<IEmployee['nom']>;
  email: FormControl<IEmployee['email']>;
  dateNaissance: FormControl<IEmployee['dateNaissance']>;
  lieuNaissance: FormControl<IEmployee['lieuNaissance']>;
  sexe: FormControl<IEmployee['sexe']>;
  telephone: FormControl<IEmployee['telephone']>;
  status: FormControl<IEmployee['status']>;
  fonction: FormControl<IEmployee['fonction']>;
  structure: FormControl<IEmployee['structure']>;
  user: FormControl<IEmployee['user']>;
};

export type EmployeeFormGroup = FormGroup<EmployeeFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class EmployeeFormService {
  createEmployeeFormGroup(employee: EmployeeFormGroupInput = { id: null }): EmployeeFormGroup {
    const employeeRawValue = {
      ...this.getFormDefaults(),
      ...employee,
    };
    return new FormGroup<EmployeeFormGroupContent>({
      id: new FormControl(
        { value: employeeRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      matricule: new FormControl(employeeRawValue.matricule, {
        validators: [Validators.required],
      }),
      prenom: new FormControl(employeeRawValue.prenom),
      nom: new FormControl(employeeRawValue.nom),
      email: new FormControl(employeeRawValue.email),
      dateNaissance: new FormControl(employeeRawValue.dateNaissance),
      lieuNaissance: new FormControl(employeeRawValue.lieuNaissance),
      sexe: new FormControl(employeeRawValue.sexe),
      telephone: new FormControl(employeeRawValue.telephone),
      status: new FormControl(employeeRawValue.status),
      fonction: new FormControl(employeeRawValue.fonction),
      structure: new FormControl(employeeRawValue.structure),
      user: new FormControl(employeeRawValue.user, {
        validators: [Validators.required],
      }),
    });
  }

  getEmployee(form: EmployeeFormGroup): IEmployee | NewEmployee {
    return form.getRawValue() as IEmployee | NewEmployee;
  }

  resetForm(form: EmployeeFormGroup, employee: EmployeeFormGroupInput): void {
    const employeeRawValue = { ...this.getFormDefaults(), ...employee };
    form.reset(
      {
        ...employeeRawValue,
        id: { value: employeeRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): EmployeeFormDefaults {
    return {
      id: null,
    };
  }
}
