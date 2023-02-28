import { axiosService } from '@eazyportal/core-ui-lib'

import React from 'react'

import ExampleComponent from './ExampleComponent'

class ExampleListComponent extends React.Component {

    state = {
        examples: null
    }

    componentDidMount() {
        axiosService.get(`/v0/sample/hello`)
            .then(response => this.setState({examples: response.data.examples}))
            .catch(error => console.log(error))
    }

    render() {
        let renderedExamples = null
        if (this.state.examples?.length) {
            renderedExamples = Object.entries(this.state.examples)
                .map(([index, example]) => {
                    return <ExampleComponent
                        key={index}
                        text={`Hello, my name is ${example.name} and I am ${example.age} years old.`}
                    />
                })
        }
        else {
            renderedExamples = <ExampleComponent text="Database is empty." />
        }

        return (
            <React.Fragment>
                {renderedExamples}
            </React.Fragment>
        )
    }

}

export default ExampleListComponent
