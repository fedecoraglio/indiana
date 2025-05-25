import { Component } from '@angular/core';
import { RouterOutlet } from '@angular/router';

@Component({
  selector: 'indiana-backoffice-app-provider',
  standalone: true,
  imports: [RouterOutlet],
  templateUrl: './provider.component.html',
})
export class ProviderComponent {}
