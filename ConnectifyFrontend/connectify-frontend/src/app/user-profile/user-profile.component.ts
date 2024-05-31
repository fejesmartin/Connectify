import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { AxiosService } from '../axios.service';
import { baseURL } from '../../assets/baseURL';
import { Post } from '../models/Post';
import { CookieService } from 'ngx-cookie-service';

@Component({
  selector: 'app-user-profile',
  templateUrl: './user-profile.component.html',
  styleUrls: ['./user-profile.component.scss']
})
export class UserProfileComponent implements OnInit {

  constructor(private route: ActivatedRoute, private axiosService: AxiosService, private cookies: CookieService) { }

  username: string | null = '';
  postcount: number = 0;
  posts: Post[] = [];

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      this.username = params.get("username");

      this.axiosService.request("GET", `${baseURL}/api/posts/getAll`, null)
        .then(response => {
          console.log("response ", response.data);
          this.posts = response.data;
          this.postcount = this.getUserPostCount();
        })
        .catch(error => {
          console.error("Error fetching posts:", error);
        });
    });
  }

  getUserPostCount(): number {
    const userId = Number(this.cookies.get("user_id"));
    return this.posts.filter(post => post.author.id === userId).length;
  }
}
