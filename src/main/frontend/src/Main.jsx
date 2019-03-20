import React, { Component } from 'react';
import { Switch, Route } from 'react-router-dom';
import Index from './Index.jsx';
import PostDetails from './PostDetails.jsx';
import AdminPanel from './AdminPanel.jsx';

class Main extends Component {

    constructor(props) {
        super(props);
    }


    render() {
        return (
            <main>
                <Switch>
                    <Route exact path='/' render={(routeProps) => (<Index {...routeProps} /> )} />
                    <Route exact path='/admin' component={AdminPanel}/>
                    <Route exact path='/posts/:id' render={(routeProps) => (<PostDetails {...routeProps} /> )}/>
                    <Route exact path='/page/:page' render={(routeProps) => (<Index {...routeProps} /> )} />
                </Switch>
            </main>
        )
    }
}

export default Main;