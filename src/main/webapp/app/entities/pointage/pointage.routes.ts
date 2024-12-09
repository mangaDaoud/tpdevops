import { Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access.service';
import { ASC } from 'app/config/navigation.constants';
import { PointageComponent } from './list/pointage.component';
import { PointageDetailComponent } from './detail/pointage-detail.component';
import { PointageUpdateComponent } from './update/pointage-update.component';
import PointageResolve from './route/pointage-routing-resolve.service';

const pointageRoute: Routes = [
  {
    path: '',
    component: PointageComponent,
    data: {
      defaultSort: 'id,' + ASC,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/view',
    component: PointageDetailComponent,
    resolve: {
      pointage: PointageResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: 'new',
    component: PointageUpdateComponent,
    resolve: {
      pointage: PointageResolve,
    },
    canActivate: [UserRouteAccessService],
  },
  {
    path: ':id/edit',
    component: PointageUpdateComponent,
    resolve: {
      pointage: PointageResolve,
    },
    canActivate: [UserRouteAccessService],
  },
];

export default pointageRoute;
