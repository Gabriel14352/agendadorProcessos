import { Routes } from '@angular/router';
import { JobsListComponent } from './components/jobs/jobs-list/jobs-list.component';
import { JobFormComponent } from './components/jobs/job-form/job-form.component';
import { JobDetailsComponent } from './components/jobs/job-details/job-details.component';

export const routes: Routes = [
  { path: '', redirectTo: 'jobs', pathMatch: 'full' },
  { path: 'jobs', component: JobsListComponent },
  { path: 'jobs/novo', component: JobFormComponent },
  { path: 'jobs/editar/:id', component: JobFormComponent },
  { path: 'jobs/detalhes/:id', component: JobDetailsComponent }
];
