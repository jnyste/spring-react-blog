import React, { Component } from 'react';
import './css/App.css';

class AdminPanel extends Component {

    constructor(props) {
        super();
        this.submitPost = this.submitPost.bind(this);
        this.submitPostEdit = this.submitPostEdit.bind(this);
        this.deletePost = this.deletePost.bind(this);
        this.startEditingPost = this.startEditingPost.bind(this);
        this.stopEditing = this.stopEditing.bind(this);
        this.state = {allPosts: [], editing: false}
    }

    componentDidMount() {
        fetch("/api/posts/all").then((res) => {
            return res.json();
        }).then((res) => {
            this.setState({allPosts: res.content})
        })
    }

    stopEditing() {
        let title_ = document.querySelector("#title");
        let body_ = document.querySelector("#body");
        let tags_ = document.querySelector("#tags");

        this.setState({editing: false});

        title_.value = "";
        body_.value = "";
        tags_.value = "";
    }

    startEditingPost(post) {
        this.setState({editing: true, postBeingEdited: post});

        fetch("/api/posts/" + post).then((res) => {
            return res.json();
        }).then((res) => {
            let title_ = document.querySelector("#title");
            let body_ = document.querySelector("#body");
            let tags_ = document.querySelector("#tags");

            title_.value = res.title;
            body_.value = res.content;
            tags_.value = res.tags.map((tag) => " " + tag.content)
        })
    }

    submitPostEdit() {
            let title_ = document.querySelector("#title").value;
            let body_ = document.querySelector("#body").value;
            let tags_ = document.querySelector("#tags").value.split(',').map((p) => p.trim());
            let obj;
            if (tags_ === "") {
                obj = {title: title_, content: body_};
            } else {
                obj = {title: title_, content: body_, tags: tags_};
            }
            fetch("/api/posts/" + this.state.postBeingEdited, {
                headers: {
                    'Content-Type': 'application/json'
                },
                method: "PUT",
                body: JSON.stringify(obj),
            }).then(() => alert("post edited"));
    }

    render() {
        return (
            <div className={"container"}>
                <div className={"row"}>
                    <div className={"six columns"}>
                        <h2>{this.state.editing ? "Edit Post" : "Add Post"}</h2>
                        <div>
                            <h3>Title</h3>
                            <input id={"title"} type={"text"} style={{width: "100%"}}/>
                        </div>
                        <div>
                            <h3>Content</h3>
                            <textarea id={"body"} style={{width: "100%", height: "200px"}}/>
                        </div>
                        <div>
                            <h3>Tags (comma-separated)</h3>
                            <input id={"tags"} type={"text"} style={{width: "100%"}}/>
                        </div>
                        <button onClick={this.state.editing ? this.submitPostEdit : this.submitPost}>{this.state.editing ? "Edit" : "Post"}</button>
                        <button onClick={this.stopEditing} style={{visibility: this.state.editing ? "visible" : "hidden"}}>Cancel</button>
                    </div>
                    <div className={"six columns"}>
                        <h2>Posts</h2>
                        { this.state.allPosts.map((post) => {
                            return <div><li className={"admin-all-posts"} key={post.id}>{post.title}<button className={"u-pull-right"} onClick={() => this.deletePost(post.id)}>Delete</button><button className={"u-pull-right"} onClick={() => this.startEditingPost(post.id)}>Edit</button><div className={"u-cf"}></div> </li></div>
                        })}
                    </div>
                </div>
            </div>
        );
    }

    deletePost(id) {
        console.log(id);
        fetch("/api/posts/delete/" + id, {
            method: "DELETE"
        }).then(() => alert("DONE!"));
    }

    submitPost() {
        let title_ = document.querySelector("#title").value;
        let body_ = document.querySelector("#body").value;
        let tags_ = document.querySelector("#tags").value.split(', ');
        let obj;
        if (tags_ === "") {
            obj = {title: title_, content: body_};
        } else {
            obj = {title: title_, content: body_, tags: tags_};
        }

        fetch("/api/posts", {
            headers: {
                'Content-Type': 'application/json'
            },
            method: "POST",
            body: JSON.stringify(obj),
        }).then(() => alert("post added"));
    }
}

export default AdminPanel;
