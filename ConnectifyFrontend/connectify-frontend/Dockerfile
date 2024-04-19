# Use official Node.js image as base
FROM node:18.13 AS build

# Set the working directory
WORKDIR /app

# Copy package.json and package-lock.json to the working directory
COPY package*.json ./

# Install dependencies
RUN npm install

# Copy the rest of the application code
COPY . .

# Build the Angular app for production
RUN npm run build

# Use NGINX as base for serving the Angular app
FROM nginx:alpine

# Remove default NGINX website
RUN rm -rf /usr/share/nginx/html/*

# Copy the built Angular app from the build stage to NGINX's html directory
COPY --from=build /app/dist/connectify-frontend /usr/share/nginx/html

# Expose port 4200
EXPOSE 4200

# Start NGINX when the container starts
CMD ["nginx", "-g", "daemon off;"]
