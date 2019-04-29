import React, { Component } from 'react';
import Header from './Header.jsx'
import { GoogleLogin } from 'react-google-login';

class PostDetails extends Component {

    constructor(props) {
        super(props);
        this.state = { post: [], likes: 0 }
        this.likePost = this.likePost.bind(this);
        this.sendComment = this.sendComment.bind(this);
        this.createNewUser = this.createNewUser.bind(this);
    }

    sendComment() {

        let commentToSend = {
            postId: this.state.post.id,
            user: localStorage.getItem("googleId"),
            content: document.querySelector(".commentField").value,
            givenName: localStorage.getItem("givenName"),
            familyName: localStorage.getItem("familyName")};

        fetch("/api/posts/" + this.state.post.id + "/comments",
            { method: "POST",
              headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
            },
              body: JSON.stringify(commentToSend)
            });
        this.setState({comments: this.state.comments.concat(commentToSend)})
    }

    createNewUser(id, _givenName, _familyName, _profileImageUrl) {
        const userToCreate = {
            googleId: id,
            givenName: _givenName,
            familyName: _familyName,
            profileImageUrl: _profileImageUrl
        };

        fetch("/api/users/" + id, {
            method: "POST",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(userToCreate)
        }).then(() => {
            localStorage.setItem("givenName", _givenName);
            localStorage.setItem("familyName", _familyName);
            localStorage.setItem("profileImageUrl", _profileImageUrl);
        })
    }

    componentDidMount() {

        let likeAmount = 0;

        fetch("/api/posts/" + this.props.match.params.id, {
            method: "GET"
        }).then(response => response.json())
            .then(data => {
                this.setState({post: data, likes: data.likes ? data.likes.length : 0});
            }).then(() => {
                fetch("/api/posts/" + this.state.post.id + "/likes/" + localStorage.getItem("googleId")).then((res) => res.json())
                    .then((json) => (
                        this.setState({postAlreadyLiked: json == null ? true : false})
                    ))
            });

        fetch("/api/posts/" + this.props.match.params.id + "/comments")
            .then(response => response.json()).then(data => {
                this.setState({comments: data})
                console.log(data);
        })
    }

    render() {

        const responseGoogle = (response) => {
            if (response.profileObj == undefined) {
                console.log("Login Failed.");
            } else {
                let googleId = response.profileObj.googleId;
                let givenName = response.profileObj.givenName;
                let familyName = response.profileObj.familyName;
                let profileImageUrl = "asd";

                localStorage.setItem("givenName", givenName);
                localStorage.setItem("familyName", familyName);
                localStorage.setItem("loggedIn", "true");
                localStorage.setItem("googleId", googleId);

                this.setState({"loggedIn": true});

                fetch("/api/users/" + googleId).then((res) => res.json()).then((usr) => {
                    if (usr == null) {
                        this.createNewUser(googleId, givenName, familyName, profileImageUrl);
                    } else {
                        localStorage.setItem("userId", usr.id);
                        localStorage.setItem("givenName", usr.givenName);
                        localStorage.setItem("familyName", usr.familyName);
                        localStorage.setItem("googleId", googleId);
                    }});
            }
        }

        return (
            <div className={"container"}>
                <Header/>
                <h1>{this.state.post.title}</h1>
                {this.state.post.content}
                <hr/>
                {localStorage.getItem("loggedIn") ? "" : <span>You need to be logged in to like a post.<br></br><br></br></span> }
                <div className={"likes-flex"}>
                    <div className={"like-call-to-action u-pull-left"}>
                        <button onClick={this.likePost} disabled={localStorage.loggedIn ? false : true}>
                            <i className="fas fa-thumbs-up" /> {this.state.postAlreadyLiked ? "LIKE" : "UNLIKE"}
                        </button>
                        <span className={"like-number"}><span id={"likesAmount"}>{this.state.likes}</span></span>
                    </div>
                    <div className={"u-pull-right posted-by"}>
                        Posted by PLACEHOLDER at {new Date(Date.parse(this.state.post.createdTime)).toGMTString()}
                    </div>
                </div>
                <h4>Have something to say? Leave a comment.</h4>
                {localStorage.getItem("loggedIn") || this.state.loggedIn ?
                    <div>
                        Commenting as {localStorage.getItem("givenName") + " " + localStorage.getItem("familyName")}
                        <textarea className={"commentField"}>
                        </textarea>
                    <div>
                        <button onClick={this.sendComment}>Comment</button></div>
                    </div>
                    :
                    <div className={"googleLogin"}> <GoogleLogin clientId="701911748021-f696rl0e3kib7jdquvrhthrtnrr9321d.apps.googleusercontent.com" buttonText="Login" onSuccess={responseGoogle} onFailure={responseGoogle} cookiePolicy={'single_host_origin'}/>
                    </div>
                }
                {this.state.comments && this.state.comments.map((comment) =>
                    <div className={"comment"}>
                        <h5>{comment.givenName + " " + comment.familyName}</h5>
                        {comment.content}
                    </div>
                )}
            </div>
        );
    }

    likePost() {

        let obj = {post: this.state.post.id,
                   userGoogleId: localStorage.getItem("googleId")};

        fetch("/api/posts/like/", {
            method: "POST",
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            },
            body: JSON.stringify(obj)

        }).then((res) => console.log(res));
        this.setState({likes: this.state.likes + 1});
        document.querySelector("button").disabled = true;
        let thumb = document.querySelector(".fa-thumbs-up");
        thumb.classList.add("thumb-animation");
        thumb.disabled = true;
    }

    paragraphify(str) {
        let pStr = "<p>";
        str.concat(pStr, str.replace('\n', '</p><p>'));
    }
}

export default PostDetails;