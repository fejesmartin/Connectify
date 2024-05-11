import { Component, numberAttribute } from '@angular/core';
import { AuthService } from '../auth-service';
import { AxiosService } from '../axios.service';
import { baseURL } from '../../assets/baseURL';
import { PostModel } from '../models/PostModel';

@Component({
  selector: 'app-createpost',
  templateUrl: './createpost.component.html',
  styleUrls: ['./createpost.component.css'] 
})
export class CreatepostComponent {

  selectedImageFile!: File;
  post:PostModel = {id: 0, content: ""};

  constructor(private authService: AuthService, private axiosService: AxiosService) {}

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
    this.post = {id: this.authService.getId(), content: commentInput.value}
    try {
        const response = this.axiosService.request

    } catch (error) {
      
    }
  }

  
}
