import { Routes } from '@angular/router';

const routes: Routes = [
  {
    path: 'authority',
    data: { pageTitle: 'devopsApp.adminAuthority.home.title' },
    loadChildren: () => import('./admin/authority/authority.routes'),
  },
  {
    path: 'employee',
    data: { pageTitle: 'devopsApp.employee.home.title' },
    loadChildren: () => import('./employee/employee.routes'),
  },
  {
    path: 'pointage',
    data: { pageTitle: 'devopsApp.pointage.home.title' },
    loadChildren: () => import('./pointage/pointage.routes'),
  },
  /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
];

export default routes;
