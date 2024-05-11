import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { CreatepostComponent } from '../createpost/createpost.component';

@Component({
  selector: 'app-postfeed',
  templateUrl: './postfeed.component.html',
  styleUrl: './postfeed.component.css'
})
export class PostfeedComponent {

  constructor(private dialog: MatDialog){

  }

  onCreatePostClick(): void{
    this.dialog.open(CreatepostComponent);
  }
}
