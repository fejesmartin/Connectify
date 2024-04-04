import { Component } from '@angular/core';
import { AxiosService } from '../axios.service';
import { User } from '../models/User';
@Component({
  selector: 'app-allusers',
  templateUrl: './allusers.component.html',
  styleUrl: './allusers.component.css',
})
export class AllusersComponent {
  data: User[] = [];

  constructor(private axiosService: AxiosService) {}

  ngOnInit(): void {
    this.axiosService.request('GET', '/api/users/getAll', null).then(response=> this.data = response.data);
  }
}
