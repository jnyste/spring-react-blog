import React, { Component } from 'react';
import Header from './Header.jsx'

class PostDetails extends Component {

    constructor(props) {
        super(props);
        this.state = { post: [] }
    }

    componentDidMount() {

        fetch("http://localhost:8080/api/posts/" + this.props.match.params.id).then(response => response.json())
            .then(data => {
                this.setState({post: data});
            })
    }

    render() {
        return (
            <div className={"container"}>
                <Header/>
                <h1>{this.state.post.title}</h1>
                {this.state.post.content}
                <br/><br/><br/>
                <p><i>Posted by PLACEHOLDER at {new Date(Date.parse(this.props.createdTime)).toString()}</i></p>
            </div>
        );
    }

    paragraphify(str) {
        let pStr = "<p>";
        str.concat(pStr, str.replace('\n', '</p><p>'));
    }
}

export default PostDetails;