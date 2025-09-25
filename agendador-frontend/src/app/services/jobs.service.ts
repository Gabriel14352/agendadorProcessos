import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class JobsService {
  private apiUrl = 'http://localhost:8080/api/jobs';

  constructor(private http: HttpClient) {}

  // Listar todos os jobs
  listarTodos(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }

  // Buscar um job por ID
  buscarPorId(id: number): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${id}`);
  }

  // Criar um novo job
  criar(job: any): Observable<any> {
    return this.http.post<any>(this.apiUrl, job);
  }

  // Editar um job existente
  editar(id: number, job: any): Observable<any> {
    return this.http.put<any>(`${this.apiUrl}/${id}`, job);
  }

  // Deletar job
  deletar(id: number): Observable<any> {
    return this.http.delete<any>(`${this.apiUrl}/${id}`);
  }

  // Rodar job manualmente
  rodarAgora(id: number): Observable<any> {
    return this.http.post<any>(`${this.apiUrl}/${id}/executar`, {});
  }

  listarExecucoes(jobId: number): Observable<any[]> {
  return this.http.get<any[]>(`${this.apiUrl}/${jobId}/execucoes`);
}

  listarArquivos(jobId: number): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/${jobId}/arquivos`);
  }
}
