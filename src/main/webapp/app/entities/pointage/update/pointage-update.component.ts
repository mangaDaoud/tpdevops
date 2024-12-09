import { Component, inject, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { finalize, map } from 'rxjs/operators';

import SharedModule from 'app/shared/shared.module';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { IEmployee } from 'app/entities/employee/employee.model';
import { EmployeeService } from 'app/entities/employee/service/employee.service';
import { IPointage } from '../pointage.model';
import { PointageService } from '../service/pointage.service';
import { PointageFormService, PointageFormGroup } from './pointage-form.service';

@Component({
  standalone: true,
  selector: 'jhi-pointage-update',
  templateUrl: './pointage-update.component.html',
  imports: [SharedModule, FormsModule, ReactiveFormsModule],
})
export class PointageUpdateComponent implements OnInit {
  isSaving = false;
  pointage: IPointage | null = null;

  employeesSharedCollection: IEmployee[] = [];

  protected pointageService = inject(PointageService);
  protected pointageFormService = inject(PointageFormService);
  protected employeeService = inject(EmployeeService);
  protected activatedRoute = inject(ActivatedRoute);

  // eslint-disable-next-line @typescript-eslint/member-ordering
  editForm: PointageFormGroup = this.pointageFormService.createPointageFormGroup();

  compareEmployee = (o1: IEmployee | null, o2: IEmployee | null): boolean => this.employeeService.compareEmployee(o1, o2);

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ pointage }) => {
      this.pointage = pointage;
      if (pointage) {
        this.updateForm(pointage);
      }

      this.loadRelationshipsOptions();
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const pointage = this.pointageFormService.getPointage(this.editForm);
    if (pointage.id !== null) {
      this.subscribeToSaveResponse(this.pointageService.update(pointage));
    } else {
      this.subscribeToSaveResponse(this.pointageService.create(pointage));
    }
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IPointage>>): void {
    result.pipe(finalize(() => this.onSaveFinalize())).subscribe({
      next: () => this.onSaveSuccess(),
      error: () => this.onSaveError(),
    });
  }

  protected onSaveSuccess(): void {
    this.previousState();
  }

  protected onSaveError(): void {
    // Api for inheritance.
  }

  protected onSaveFinalize(): void {
    this.isSaving = false;
  }

  protected updateForm(pointage: IPointage): void {
    this.pointage = pointage;
    this.pointageFormService.resetForm(this.editForm, pointage);

    this.employeesSharedCollection = this.employeeService.addEmployeeToCollectionIfMissing<IEmployee>(
      this.employeesSharedCollection,
      pointage.employee,
    );
  }

  protected loadRelationshipsOptions(): void {
    this.employeeService
      .query()
      .pipe(map((res: HttpResponse<IEmployee[]>) => res.body ?? []))
      .pipe(
        map((employees: IEmployee[]) =>
          this.employeeService.addEmployeeToCollectionIfMissing<IEmployee>(employees, this.pointage?.employee),
        ),
      )
      .subscribe((employees: IEmployee[]) => (this.employeesSharedCollection = employees));
  }
}
