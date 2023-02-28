import React from 'react'

import ExampleComponent from './components/ExampleComponent'
import ExampleListComponent from './components/ExampleListComponent'

import source from './App.module.css'
import UploadFormComponent from "./components/UploadFormComponent";

class App extends React.Component {

    state = {
        loadData: false,
    }

    render() {
        let renderedComponent
        if (this.state.loadData) {
            renderedComponent = <ExampleListComponent/>
        } else {
            renderedComponent = <ExampleComponent text="Create React Library Example 😄"/>
        }

        return (<div>
                <UploadFormComponent/>
                <div className={source.loadDataWrapper}>
                    <button className={source.loadDataButton} onClick={this.buttonClickHandler}>Load data from backend
                    </button>
                </div>
                <div>
                    {renderedComponent}
                </div>
            </div>);
    }
}

export default App
