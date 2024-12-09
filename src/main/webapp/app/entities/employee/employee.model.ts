import dayjs from 'dayjs/esm';
import { IUser } from 'app/entities/user/user.model';
import { Sexe } from 'app/entities/enumerations/sexe.model';

export interface IEmployee {
  id: number;
  matricule?: string | null;
  prenom?: string | null;
  nom?: string | null;
  email?: string | null;
  dateNaissance?: dayjs.Dayjs | null;
  lieuNaissance?: string | null;
  sexe?: keyof typeof Sexe | null;
  telephone?: string | null;
  status?: string | null;
  fonction?: string | null;
  structure?: string | null;
  user?: Pick<IUser, 'id'> | null;
}

export type NewEmployee = Omit<IEmployee, 'id'> & { id: null };
