import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ActivatedRoute, Router } from '@angular/router';
import { JobsService } from '../../../services/jobs.service';

@Component({
  selector: 'app-job-details',
  standalone: true, 
  imports: [CommonModule], 
  templateUrl: './job-details.component.html',
  styleUrls: ['./job-details.component.css']
})
export class JobDetailsComponent implements OnInit {
  jobId!: number;
  execucoes: any[] = [];
  arquivos: any[] = [];

  constructor(
    private jobsService: JobsService,
    private route: ActivatedRoute,
    private router: Router
  ) {}

  ngOnInit(): void {
    const id = this.route.snapshot.paramMap.get('id');
    if (id) {
      this.jobId = +id;
      this.carregarDetalhes();
    }
  }

  carregarDetalhes() {
    // Buscar execuções do job
    this.jobsService.listarExecucoes(this.jobId).subscribe({
      next: (data) => (this.execucoes = data),
      error: (err) => console.error('Erro ao carregar execuções do job', err),
    });

    // Buscar arquivos associados ao job
    this.jobsService.listarArquivos(this.jobId).subscribe({
      next: (data) => (this.arquivos = data),
      error: (err) => console.error('Erro ao carregar arquivos do job', err),
    });
  }

  voltar() {
    this.router.navigate(['/jobs']);
  }
}
