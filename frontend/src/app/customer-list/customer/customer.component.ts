import {Component, Input} from '@angular/core';
import {Customer} from '../../shared/domain/Customer';

@Component({
  selector: 'app-customer',
  templateUrl: './customer.component.html',
  styleUrls: ['./customer.component.scss']
})
export class CustomerComponent {
  @Input() customer: Customer;

}
