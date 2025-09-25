import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router, RouterModule } from '@angular/router';
import { JobsService } from '../../../services/jobs.service';

@Component({
  selector: 'app-jobs-list',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './jobs-list.component.html',
  styleUrls: ['./jobs-list.component.css']
})
export class JobsListComponent implements OnInit {
  jobs: any[] = [];

  constructor(
    private jobsService: JobsService,
    private router: Router // 👈 agora o Router é injetado corretamente
  ) {}

  ngOnInit(): void {
    this.carregarJobs();
  }

  carregarJobs() {
    this.jobsService.listarTodos().subscribe({
      next: (data) => (this.jobs = data),
      error: (err) => console.error('Erro ao carregar jobs', err),
    });
  }

  novoAgendamento() {
    this.router.navigate(['/jobs/novo']);
  }

  editarJob(id: number) {
    this.router.navigate(['/jobs/editar', id]);
  }

  detalhesJob(id: number) {
    this.router.navigate(['/jobs/detalhes', id]);
  }

  deletarJob(id: number) {
    if (confirm('Deseja realmente excluir este job?')) {
      this.jobsService.deletar(id).subscribe({
        next: () => this.carregarJobs(),
        error: (err) => alert('Erro ao excluir: ' + err.message),
      });
    }
  }
}
