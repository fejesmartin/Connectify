import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.scss']
})
export class UserProfileComponent implements OnInit {

  constructor(private route: ActivatedRoute) { }
  username: string|null = '';

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      this.username = params.get("username");

    })
  };

}