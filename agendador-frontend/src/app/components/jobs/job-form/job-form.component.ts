import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';
import { JobsService } from '../../../services/jobs.service';
import { CronPickerComponent } from '../../cron-picker/cron-picker.component';

@Component({
  selector: 'app-job-form',
  standalone: true,
  imports: [CommonModule, FormsModule, CronPickerComponent],
  templateUrl: './job-form.component.html',
  styleUrls: ['./job-form.component.css'],
})
export class JobFormComponent implements OnInit {
  job: any = { nome: '', cronExpression: '', status: 'AGENDADO' };
  isEdit = false;

  constructor(
    private jobsService: JobsService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.isEdit = true;
      this.jobsService.buscarPorId(+id).subscribe({
        next: (data) => (this.job = data),
        error: (err) => console.error('Erro ao buscar job', err),
      });
    }
  }

  salvar() {
    if (!this.job.nome || !this.job.cronExpression) {
      alert('Nome e expressão CRON são obrigatórios!');
      return;
    }

    const req$ = this.isEdit
      ? this.jobsService.editar(this.job.id, this.job)
      : this.jobsService.criar(this.job);

    req$.subscribe({
      next: () => this.router.navigate(['/jobs']),
      error: (err) => alert('Erro ao salvar job: ' + (err?.error?.message || err.message)),
    });
  }

  cancelar() {
    this.router.navigate(['/jobs']);
  }
}
