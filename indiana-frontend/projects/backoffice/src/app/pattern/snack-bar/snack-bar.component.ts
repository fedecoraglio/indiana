import { Component, inject, ViewEncapsulation } from '@angular/core';
import {
  MAT_SNACK_BAR_DATA,
  MatSnackBarModule,
} from '@angular/material/snack-bar';
import { CommonModule } from '@angular/common';

export type SnackBarOptions = Readonly<{
  type: 'error' | 'success';
  title?: string;
  message: string;
  hasIcon?: boolean;
}>;

@Component({
  selector: 'indiana-backoffice-snack-bar',
  templateUrl: './snack-bar.component.html',
  standalone: true,
  imports: [CommonModule, MatSnackBarModule],
  encapsulation: ViewEncapsulation.None,
  styleUrls: ['./snack-bar.component.scss'],
})
export class SnackBarComponent {
  readonly data: SnackBarOptions = inject(MAT_SNACK_BAR_DATA);
}
