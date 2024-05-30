import { Component, OnInit } from '@angular/core';
import { AxiosService } from '../axios.service';
import { Post } from '../models/Post';
import { Router } from '@angular/router';

@Component({
  selector: 'app-feed',
  templateUrl: './feed.component.html',
  styleUrls: ['./feed.component.css']
})
export class FeedComponent implements OnInit {
  posts: Post[] = [];
  constructor(private axiosService: AxiosService, private router: Router) {}

  ngOnInit(): void {
    this.refreshFeed();
  }

  refreshFeed(): void {
    this.axiosService.request("GET", "/api/posts/getAll", null)
      .then(response => {
        this.posts = response.data;
      })
      .catch(error => {
        console.error('Error fetching posts:', error);
      });
  }

  visitUserProfile(username: string): void {
    this.router.navigate(["/visit", username]);
  }

  addFriend(username: string): void {
    this.axiosService.request("POST", `/api/friends/add/${username}`, null)
      .then(response => {
        console.log('Friend request sent:', response);
      })
      .catch(error => {
        console.error('Error sending friend request:', error);
      });
  }

  commentPost(postId: number): void {
    console.log('Commenting on post:', postId);
    // Implement comment functionality
  }

  sharePost(postId: number): void {
    console.log('Sharing post:', postId);
    // Implement share functionality
  }
}
