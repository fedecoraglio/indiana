import {Component, Input, Output, EventEmitter, forwardRef, OnInit} from '@angular/core';
import {
  ControlValueAccessor,
  FormsModule,
  NG_VALUE_ACCESSOR,
  ReactiveFormsModule
} from '@angular/forms';
import {CommonModule} from "@angular/common";

export interface DropdownItem {
  key: string;
  value: string;
}

@Component({
  selector: 'indiana-backoffice-custom-dropdown',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule, FormsModule],
  templateUrl: './custom-dropdown.component.html',
  styleUrls: ['./custom-dropdown.component.scss'],
  providers: [
    {
      provide: NG_VALUE_ACCESSOR,
      useExisting: forwardRef(() => CustomDropdownComponent),
      multi: true
    }
  ]
})
export class CustomDropdownComponent implements OnInit, ControlValueAccessor {
  @Input() items: DropdownItem[] = [];
  @Output() filterChanged = new EventEmitter<string[]>();
  @Output() radioSelectionChanged = new EventEmitter<string>();

  selectedItems: Set<string> = new Set<string>();
  radioSelection: string = 'Standard';
  isOpen = false;
  searchQuery = '';
  filteredItems: DropdownItem[] = [];

  // ControlValueAccessor methods
  onChange = (value: any) => {};
  onTouched = () => {};

  constructor() {}

  ngOnInit() {
    // Inicializa con todos los elementos seleccionados
    this.items.forEach(item => this.selectedItems.add(item.key));
    this.filteredItems = [...this.items];
  }

  writeValue(value: any): void {
    if (value) {
      this.selectedItems = new Set(value);
    }
  }

  registerOnChange(fn: any): void {
    this.onChange = fn;
  }

  registerOnTouched(fn: any): void {
    this.onTouched = fn;
  }

  toggleDropdown() {
    this.isOpen = !this.isOpen;
  }

  toggleItemSelection(key: string) {
    if (this.selectedItems.has(key)) {
      this.selectedItems.delete(key);
    } else {
      this.selectedItems.add(key);
    }
    this.emitFilterChange();
    this.onChange(Array.from(this.selectedItems));
  }

  selectAll() {
    this.items.forEach(item => this.selectedItems.add(item.key));
    this.emitFilterChange();
  }

  selectNone() {
    this.selectedItems.clear();
    this.emitFilterChange();
  }

  onRadioChange(value: string) {
    this.radioSelection = value;
    this.radioSelectionChanged.emit(value);
  }

  emitFilterChange() {
    this.filterChanged.emit(Array.from(this.selectedItems));
  }

  get selectedCount(): number {
    return this.selectedItems.size;
  }

  filterItems() {
    this.filteredItems = this.items.filter(item =>
        item.value.toLowerCase().includes(this.searchQuery.toLowerCase())
    );
  }
}
