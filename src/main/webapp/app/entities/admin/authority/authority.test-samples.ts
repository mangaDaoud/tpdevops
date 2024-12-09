import { IAuthority, NewAuthority } from './authority.model';

export const sampleWithRequiredData: IAuthority = {
  name: 'be8e38ef-f3e9-4296-9d75-0f3903ca5c8f',
};

export const sampleWithPartialData: IAuthority = {
  name: '8201d0f4-9b18-4f8b-8a9c-4bfcd68d804d',
};

export const sampleWithFullData: IAuthority = {
  name: '7b679002-0df3-4002-ac04-6495485aa583',
};

export const sampleWithNewData: NewAuthority = {
  name: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
