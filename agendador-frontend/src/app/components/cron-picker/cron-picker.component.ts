import { Component, EventEmitter, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import cronstrue from 'cronstrue';

@Component({
  selector: 'app-cron-picker',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './cron-picker.component.html',
  styleUrls: ['./cron-picker.component.css'],
})
export class CronPickerComponent {
  @Output() cronChange = new EventEmitter<string>();

  // valores padrão (Quartz: 6 campos -> segundos + minutos + horas + diaMês + mês + diaSemana)
  seconds: string = '0';
  minutes: string = '0';
  hours: string = '0';
  dayOfMonth: string = '*';
  month: string = '*';
  dayOfWeek: string = '?'; // Regra Quartz: se diaDoMês não for "?" então diaDaSemana deve ser "?" e vice-versa

  cronExpression = '';

  updateCron() {
    // Garante a regra do Quartz: apenas UM entre dia do mês e dia da semana pode ser específico; o outro vira "?"
    const dom = (this.dayOfMonth ?? '').trim();
    const dow = (this.dayOfWeek ?? '').trim();

    if (dom !== '*' && dom !== '?') {
      this.dayOfWeek = '?';
    } else if (dow !== '*' && dow !== '?') {
      this.dayOfMonth = '?';
    }

    this.cronExpression = `${this.seconds} ${this.minutes} ${this.hours} ${this.dayOfMonth} ${this.month} ${this.dayOfWeek}`;
    this.cronChange.emit(this.cronExpression);
  }
}
