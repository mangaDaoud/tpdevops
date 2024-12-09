import dayjs from 'dayjs/esm';

import { IEmployee, NewEmployee } from './employee.model';

export const sampleWithRequiredData: IEmployee = {
  id: 14702,
  matricule: 'underneath joyfully incidence',
};

export const sampleWithPartialData: IEmployee = {
  id: 7485,
  matricule: 'downstairs',
  nom: 'amidst loyal before',
  email: 'Delphine.Lockman26@gmail.com',
  dateNaissance: dayjs('2024-12-09'),
  telephone: '(314) 989-4747',
  fonction: 'thoroughly phooey',
  structure: 'until',
};

export const sampleWithFullData: IEmployee = {
  id: 7704,
  matricule: 'meanwhile pish',
  prenom: 'modulo too aha',
  nom: 'which gadzooks',
  email: 'Jamey.Heaney-Koss90@hotmail.com',
  dateNaissance: dayjs('2024-12-09'),
  lieuNaissance: 'actually dreary',
  sexe: 'Masculin',
  telephone: '(849) 525-3202 x405',
  status: 'before newsprint',
  fonction: 'race lumpy drop',
  structure: 'whereas viscose angina',
};

export const sampleWithNewData: NewEmployee = {
  matricule: 'nor enterprise paw',
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
