import { Routes } from '@angular/router';
import { authGuard, publicGuard } from './guards/auth.guard';
import { Login } from './components/login/login';
import { Register } from './components/register/register';
import { Board } from './components/board/board';

export const routes: Routes = [
  { path: '', redirectTo: '/board', pathMatch: 'full' },
  { path: 'login', component: Login, canActivate: [publicGuard] },
  { path: 'register', component: Register, canActivate: [publicGuard] },
  { path: 'board', component: Board, canActivate: [authGuard] },
  { path: '**', redirectTo: '/board' }
];
