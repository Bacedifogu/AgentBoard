import { Component, signal } from '@angular/core';
import { Router, RouterLink } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { AuthService } from '../../services';

@Component({
  selector: 'app-register',
  standalone: true,
  imports: [FormsModule, RouterLink],
  templateUrl: './register.component.html',
  styleUrl: './register.component.scss',
})
export class RegisterComponent {
  username = '';
  email = '';
  password = '';
  confirmPassword = '';
  error = signal('');
  loading = signal(false);

  constructor(private authService: AuthService, private router: Router) {}

  onSubmit() {
    if (!this.username || !this.email || !this.password) {
      this.error.set('Please fill in all fields');
      return;
    }

    if (this.password !== this.confirmPassword) {
      this.error.set('Passwords do not match');
      return;
    }

    this.loading.set(true);
    this.error.set('');

    this.authService.register({
      username: this.username,
      email: this.email,
      password: this.password
    }).subscribe({
      next: (response) => {
        this.authService.setSession(response);
        this.router.navigate(['/board']);
      },
      error: (err) => {
        this.error.set(err.error?.message || 'Registration failed');
        this.loading.set(false);
      }
    });
  }
}
