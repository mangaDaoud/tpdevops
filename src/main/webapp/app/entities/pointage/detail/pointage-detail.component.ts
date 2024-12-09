import { Component, Input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe } from 'app/shared/date';
import { IPointage } from '../pointage.model';

@Component({
  standalone: true,
  selector: 'jhi-pointage-detail',
  templateUrl: './pointage-detail.component.html',
  imports: [SharedModule, RouterModule, DurationPipe, FormatMediumDatetimePipe, FormatMediumDatePipe],
})
export class PointageDetailComponent {
  @Input() pointage: IPointage | null = null;

  previousState(): void {
    window.history.back();
  }
}
