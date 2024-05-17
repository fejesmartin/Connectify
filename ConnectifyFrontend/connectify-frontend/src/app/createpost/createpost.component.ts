import { Component, numberAttribute } from '@angular/core';
import { AuthService } from '../auth-service';
import { AxiosService } from '../axios.service';
import { baseURL } from '../../assets/baseURL';
import { PostModel } from '../models/PostModel';
import { CookieService } from 'ngx-cookie-service';
import { MatDialog } from '@angular/material/dialog';

@Component({
  selector: 'app-createpost',
  templateUrl: './createpost.component.html',
  styleUrls: ['./createpost.component.css'] 
})
export class CreatepostComponent {

  selectedImageFile!: File;
  post:PostModel = {authorId: 0, content: ""};

  constructor(private authService: AuthService, private axiosService: AxiosService, private cookieService: CookieService, private dialog: MatDialog) {}

  onPhotoSelected(photoSelector: HTMLInputElement) {
    this.selectedImageFile = photoSelector.files![0];
    if (!this.selectedImageFile) return;

    const fileReader = new FileReader();
    fileReader.readAsDataURL(this.selectedImageFile);
    fileReader.addEventListener('loadend', (ev) => {
      const readableString = fileReader.result as string;
      const postPreviewImage = document.getElementById("post-preview-image") as HTMLImageElement;
      postPreviewImage.src = readableString;
    });
  }

  onPostClick(commentInput: HTMLTextAreaElement): void{
    this.post = {authorId: Number(this.cookieService.get("user_id")), content: commentInput.value}
    try {
        this.axiosService.request("POST",baseURL+"/api/posts/create",this.post);
        console.log(this.post.authorId);
        
        console.log("Post successfully created");
        alert("Post successfully created. Refresh feed to see your post!")
        this.dialog.closeAll();

    } catch (error) {
        console.error("Error during creating post: ", error);
        
    }
  }

  
}
