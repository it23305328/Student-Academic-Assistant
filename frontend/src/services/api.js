import axios from 'axios';

// Create a configured Axios instance
// Note: Changed to port 8081 since we updated the backend application.properties earlier to avoid port conflicts
const apiClient = axios.create({
    baseURL: 'http://localhost:8082/',
    headers: {
        'Content-Type': 'application/json'
    }
});

// Response interceptor for basic error handling
apiClient.interceptors.response.use(
    response => {
        // Return the response if it was successful
        return response;
    },
    error => {
        // Handle network errors (backend down)
        if (!error.response && error.message === 'Network Error') {
            alert('Network Error: The backend server appears to be unreachable. Please ensure it is running.');
        } else if (error.response) {
            // Log other generic server errors
            console.error(`API Error - Status: ${error.response.status}`, error.response.data);

            // Optionally, we could show an alert for 500 internal server errors
            if (error.response.status >= 500) {
                alert('An unexpected server error occurred. Please try again later.');
            }
        }
        return Promise.reject(error);
    }
);

export default apiClient;
