import dayjs from 'dayjs/esm';
import { IEmployee } from 'app/entities/employee/employee.model';

export interface IPointage {
  id: number;
  datuDuJour?: dayjs.Dayjs | null;
  heureArrive?: string | null;
  heureDepart?: string | null;
  employee?: Pick<IEmployee, 'id'> | null;
}

export type NewPointage = Omit<IPointage, 'id'> & { id: null };
