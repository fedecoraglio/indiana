import { ChangeDetectionStrategy, Component } from '@angular/core';
import {
  CustomDropdownComponent,
  DropdownItem,
} from './custom-dropdown/custom-dropdown.component';

@Component({
  selector: 'indiana-backoffice-home',
  standalone: true,
  imports: [CustomDropdownComponent],
  templateUrl: './home.component.html',
  styleUrl: './home.component.scss',
  changeDetection: ChangeDetectionStrategy.OnPush,
})
export class HomeComponent {
  items: DropdownItem[] = [
    { key: 'val1', value: 'val1' },
    { key: 'val2', value: 'val2' },
    { key: 'val3', value: 'val3'},
    { key: 'val4', value: 'val3'},
    { key: 'val5', value: 'val3'},
    { key: 'val6', value: 'val3'},
    { key: 'val7', value: 'val3'},
    { key: 'val8', value: 'val3'},
    { key: 'val9', value: 'val3'},
    { key: 'val10', value: 'val3'},
    { key: 'val11', value: 'val3'},
    { key: 'val12', value: 'val3'},
    { key: 'val13', value: 'val3'},
    { key: 'val14', value: 'val3'},
    { key: 'val15', value: 'val3'},
    { key: 'val16', value: 'val3'},
    { key: 'val17', value: 'val3'},
    { key: 'val18', value: 'val3'},
    { key: 'val19', value: 'val3'},
    { key: 'val20', value: 'val3'},
    { key: 'val21', value: 'val3'},
    { key: 'val22', value: 'val3'},
    { key: 'val23', value: 'val3'},
    { key: 'val24', value: 'val3'},
    { key: 'val25', value: 'val3'},
    { key: 'val26', value: 'val3'},
    { key: 'val27', value: 'val3'},
    { key: 'val28', value: 'val3'},
    { key: 'val29', value: 'val3'},
    { key: 'val30', value: 'val3'},
  ];
}
