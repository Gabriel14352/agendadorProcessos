import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, RouterModule],
  template: `
    <div class="app-container">
      <h1>Agendador de Jobs</h1>
      <router-outlet></router-outlet>
    </div>
  `,
  styles: [`
    .app-container {
      font-family: Arial, sans-serif;
      margin: 20px;
    }
  `]
})
export class AppComponent {}
