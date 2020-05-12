import { NgModule } from '@angular/core';
import { Router, Routes, RouterModule } from '@angular/router';
import {HomeComponent} from './components/user/home/home.component';
import {LoginComponent} from './components/user/login/login.component';
import {RegisterComponent} from './components/admin/register/register.component';
import {ProfileComponent} from './components/user/profile/profile.component';
import {DetailComponent} from './components/user/detail/detail.component';
import {DashboardComponent} from './components/admin/dashboard/dashboard.component';
import {UserListComponent} from './components/admin/user-list/user-list.component';
import {ProductListComponent} from './components/admin/product-list/product-list.component';
import {UnathorizedComponent} from './components/error/unathorized/unathorized.component';
import {NotFoundComponent} from './components/error/not-found/not-found.component';
import {TransactionListComponent} from './components/admin/transaction-list/transaction-list.component';
import {AuthGuard} from './guards/auth.guard';
import {Role} from './model/role';
import { CoursBbeComponent } from './components/admin/cours-bbe/cours-bbe.component';
import { SettingsComponent } from './components/admin/settings/settings.component';
import { CourbeBdtComponent } from './components/admin/courbe-bdt/courbe-bdt.component';

const routes: Routes = [
  //Main page
  {path: '', redirectTo: 'home', pathMatch: 'full'},
  {path: 'home', component: HomeComponent},
  //User pages
  {path: 'login', component: LoginComponent},
  {path: 'profile',
  component: ProfileComponent,
  canActivate: [AuthGuard]
  },
  {path: 'detail', component: DetailComponent},
  {path: 'detail/:id', component: DetailComponent},

  //admin panel
  {path: 'dashboard',
  component: DashboardComponent,
  canActivate: [AuthGuard],
  data: {roles: [Role.ADMIN]}
  },
  {path: 'register', 
  component: RegisterComponent,
  canActivate: [AuthGuard],
  data: {roles: [Role.ADMIN]}
  },
  {path: 'user-list',
  component: UserListComponent,
  canActivate: [AuthGuard],
  data: {roles: [Role.ADMIN]}
  },
  {path: 'cours-bbe',
  component: CoursBbeComponent,
  canActivate: [AuthGuard],
  data: {roles: [Role.ADMIN]}
  },
  {path: 'courbe-bdt',
  component: CourbeBdtComponent,
  canActivate: [AuthGuard],
  data: {roles: [Role.ADMIN]}
  },
  {path: 'settings',
  component: SettingsComponent,
  canActivate: [AuthGuard],
  data: {roles: [Role.ADMIN]}
  },
  {path: 'product-list',
  component: ProductListComponent,
  canActivate: [AuthGuard],
  data: {roles: [Role.ADMIN]}
 },
 {path: 'transaction-list',
 component: TransactionListComponent,
 canActivate: [AuthGuard],
 data: {roles: [Role.ADMIN]}
},

  //error pages
  {path: '404', component: NotFoundComponent},
  {path: '401', component: UnathorizedComponent},
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule {
constructor(private router: Router) {
  this.router.errorHandler = (error: any) => {
    this.router.navigate(['/404']);
  }
}
}
