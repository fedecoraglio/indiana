import { Component } from '@angular/core';
import { MainLayoutComponent } from './layout/main-layout/main-layout.component';

@Component({
  selector: 'indiana-backoffice-root',
  standalone: true,
  imports: [MainLayoutComponent],
  template: `<indiana-backoffice-main-layout></indiana-backoffice-main-layout>`,
})
export class AppComponent {
  title = 'backoffice';
}
