import dayjs from 'dayjs/esm';

import { IPointage, NewPointage } from './pointage.model';

export const sampleWithRequiredData: IPointage = {
  id: 5381,
};

export const sampleWithPartialData: IPointage = {
  id: 9842,
};

export const sampleWithFullData: IPointage = {
  id: 14913,
  datuDuJour: dayjs('2024-12-09'),
  heureArrive: 'hot',
  heureDepart: 'con',
};

export const sampleWithNewData: NewPointage = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
