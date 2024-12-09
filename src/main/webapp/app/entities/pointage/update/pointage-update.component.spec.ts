import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { RouterTestingModule } from '@angular/router/testing';
import { of, Subject, from } from 'rxjs';

import { IEmployee } from 'app/entities/employee/employee.model';
import { EmployeeService } from 'app/entities/employee/service/employee.service';
import { PointageService } from '../service/pointage.service';
import { IPointage } from '../pointage.model';
import { PointageFormService } from './pointage-form.service';

import { PointageUpdateComponent } from './pointage-update.component';

describe('Pointage Management Update Component', () => {
  let comp: PointageUpdateComponent;
  let fixture: ComponentFixture<PointageUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let pointageFormService: PointageFormService;
  let pointageService: PointageService;
  let employeeService: EmployeeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule, RouterTestingModule.withRoutes([]), PointageUpdateComponent],
      providers: [
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(PointageUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PointageUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    pointageFormService = TestBed.inject(PointageFormService);
    pointageService = TestBed.inject(PointageService);
    employeeService = TestBed.inject(EmployeeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('Should call Employee query and add missing value', () => {
      const pointage: IPointage = { id: 456 };
      const employee: IEmployee = { id: 21701 };
      pointage.employee = employee;

      const employeeCollection: IEmployee[] = [{ id: 20302 }];
      jest.spyOn(employeeService, 'query').mockReturnValue(of(new HttpResponse({ body: employeeCollection })));
      const additionalEmployees = [employee];
      const expectedCollection: IEmployee[] = [...additionalEmployees, ...employeeCollection];
      jest.spyOn(employeeService, 'addEmployeeToCollectionIfMissing').mockReturnValue(expectedCollection);

      activatedRoute.data = of({ pointage });
      comp.ngOnInit();

      expect(employeeService.query).toHaveBeenCalled();
      expect(employeeService.addEmployeeToCollectionIfMissing).toHaveBeenCalledWith(
        employeeCollection,
        ...additionalEmployees.map(expect.objectContaining),
      );
      expect(comp.employeesSharedCollection).toEqual(expectedCollection);
    });

    it('Should update editForm', () => {
      const pointage: IPointage = { id: 456 };
      const employee: IEmployee = { id: 31094 };
      pointage.employee = employee;

      activatedRoute.data = of({ pointage });
      comp.ngOnInit();

      expect(comp.employeesSharedCollection).toContain(employee);
      expect(comp.pointage).toEqual(pointage);
    });
  });

  describe('save', () => {
    it('Should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPointage>>();
      const pointage = { id: 123 };
      jest.spyOn(pointageFormService, 'getPointage').mockReturnValue(pointage);
      jest.spyOn(pointageService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pointage });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: pointage }));
      saveSubject.complete();

      // THEN
      expect(pointageFormService.getPointage).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(pointageService.update).toHaveBeenCalledWith(expect.objectContaining(pointage));
      expect(comp.isSaving).toEqual(false);
    });

    it('Should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPointage>>();
      const pointage = { id: 123 };
      jest.spyOn(pointageFormService, 'getPointage').mockReturnValue({ id: null });
      jest.spyOn(pointageService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pointage: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: pointage }));
      saveSubject.complete();

      // THEN
      expect(pointageFormService.getPointage).toHaveBeenCalled();
      expect(pointageService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('Should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPointage>>();
      const pointage = { id: 123 };
      jest.spyOn(pointageService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ pointage });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(pointageService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });

  describe('Compare relationships', () => {
    describe('compareEmployee', () => {
      it('Should forward to employeeService', () => {
        const entity = { id: 123 };
        const entity2 = { id: 456 };
        jest.spyOn(employeeService, 'compareEmployee');
        comp.compareEmployee(entity, entity2);
        expect(employeeService.compareEmployee).toHaveBeenCalledWith(entity, entity2);
      });
    });
  });
});
