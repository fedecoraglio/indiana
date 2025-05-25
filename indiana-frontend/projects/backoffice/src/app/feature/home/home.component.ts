import { ChangeDetectionStrategy, Component } from '@angular/core';
import { MatCardModule } from "@angular/material/card";
import { MatIconModule } from "@angular/material/icon";
import { NgFor } from "@angular/common";
import { RouterModule, Router } from '@angular/router';

@Component({
  selector: 'indiana-backoffice-home',
  standalone: true,
  imports: [RouterModule, MatCardModule, MatIconModule,NgFor],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class HomeComponent {

  readonly menuItems = [
    { title: 'Location', icon: 'location_on', route: '/location' },
    { title: 'Warehouse', icon: 'store', route: '/warehouse' },
    { title: 'Product', icon: 'inventory_2', route: '/product' },
    { title: 'Provider', icon: 'supervisor_account', route: '/provider' },
    { title: 'Order', icon: 'shopping_cart', route: '/order' },
  ];

  constructor(private router: Router) {}

  navigateTo(route: string) {
    this.router.navigate([route]).then();
  }
}
