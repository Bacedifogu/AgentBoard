import { Routes } from '@angular/router';
import { authGuard, publicGuard } from './guards/auth.guard';
import { LoginComponent } from './components/login/login.component';
import { RegisterComponent } from './components/register/register.component';
import { BoardComponent } from './components/board/board.component';

export const routes: Routes = [
  { path: '', redirectTo: '/board', pathMatch: 'full' },
  { path: 'login', component: LoginComponent, canActivate: [publicGuard] },
  { path: 'register', component: RegisterComponent, canActivate: [publicGuard] },
  { path: 'board', component: BoardComponent, canActivate: [authGuard] },
  { path: '**', redirectTo: '/board' }
];
