import React, { Component } from 'react';
import './css/App.css';

class AdminPanel extends Component {

    constructor(props) {
        super();
        this.submitPost = this.submitPost.bind(this);
        this.deletePost = this.deletePost.bind(this);
        this.state = {allPosts: []}
    }

    componentDidMount() {
        fetch("http://localhost:8080/api/posts/all").then((res) => {
            return res.json();
        }).then((res) => {
            this.setState({allPosts: res.content})
        })
    }

    render() {
        return (
            <div>
                <p>Cool Admin Panel</p>

                Add new post
                <div>Title<input id={"title"} type={"text"}/></div>
                Post<div><textarea id={"body"}/></div>
                <button onClick={this.submitPost}>POST!!</button>
                <hr/>
                <hr/>
                <hr/>
                <div>Remove post</div>
                { this.state.allPosts.map((post) => {
                    return <li key={post.id}>{post.title} <button onClick={() => this.deletePost(post.id)}>Delete</button> </li>
                })}
            </div>
        );
    }

    deletePost(id) {
        console.log(id);
        fetch("http://localhost:8080/api/posts/delete/" + id, {
            method: "DELETE"
        }).then(() => alert("DONE!"));
    }

    submitPost() {
        let title_ = document.querySelector("#title").value;
        let body_ = document.querySelector("#body").value;
        let obj = {title: title_, content: body_};

        fetch("http://localhost:8080/api/posts", {
            headers: {
                'Content-Type': 'application/json'
            },
            method: "POST",
            body: JSON.stringify(obj),
        }).then(() => alert("post added"));
    }
}

export default AdminPanel;
