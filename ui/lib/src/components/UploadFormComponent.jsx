import React from 'react'
import source from '../App.module.css'
import {axiosService} from "@eazyportal/core-ui-lib";

class UploadFormComponent extends React.Component {

    constructor(props) {
        super(props)
        this.state = {
            name: '',
            file: null,
            type: "TaxReturnPeriod",
            taxReturnPeriodYear: new Date().getFullYear(),
            taxReturnPeriodQuarter: 3,
            taxReturnPeriodMonth: "MARCH"
        }


        this.handleNameChange = this.handleNameChange.bind(this);
        this.formSubmitHandler = this.formSubmitHandler.bind(this);
    }

    handleNameChange = (event) => {
        this.setState({name: event.target.value})
        console.log(name)
    }

    handleFileChange = (event) => {
        this.setState({file: event.target.files[0]})
        console.log(name)
    }

    formSubmitHandler = (event) => {
        const formData = new FormData();
        let document = {
            memberId: '0b1e0c6c-710b-4641-8530-ee9beb760db9',
            name: this.state.name,
            documentType: this.state.type,
            metadata: {
                taxReturnPeriod: {
                    year: this.state.taxReturnPeriodYear,
                    quarter: this.state.taxReturnPeriodQuarter,
                    month: this.state.taxReturnPeriodMonth,
                }
            }
        }
        const jsonString = JSON.stringify(document)
        const jsonBlob = new Blob([jsonString], {
            type: 'application/json'
        });

        formData.append("file", this.state.file);
        formData.append("document", jsonBlob);
        const headers = {
            'Accept': 'application/json',
            'Content-Type': 'multipart/form-data',
        }
        axiosService.post("/v1/documents", formData, headers)
        event.preventDefault()
    }


    render() {
        const labelStyle = {display: 'block'}
        let quarterOptions = []
        for(let i = 1; i < 5; i++) {
            quarterOptions.push(<option key={i} value={i}>{i}</option>)
        }

        let monthOptions = []
        for (let i = 0; i < 12; i++) {
            const value = new Date(0, i).toLocaleString('en', { month: 'long' }).toUpperCase()
            const text = new Date(0, i).toLocaleString('hu', { month: 'long' })
            monthOptions.push(<option key={value} value={value}>{text}</option>)
        }

        return (
            <div>
                <div className={source.loadDataWrapper}>
                    <form onSubmit={this.formSubmitHandler}>
                        <label style={labelStyle}>
                            Document Type:
                            <input type="text" value={this.state.type}
                                   onChange={(e) => this.setState({type: e.target.value})}/>
                        </label>
                        <label style={labelStyle}>
                            Filename:
                            <input type="text" value={this.state.name} onChange={this.handleNameChange}/>
                        </label>
                        <label style={labelStyle}>
                            File: <input type="file" onChange={this.handleFileChange}/>
                        </label>
                        <label style={labelStyle}>
                            TaxStatePeriod Year:
                            <input type="text" value={this.state.taxReturnPeriodYear}
                                   onChange={(e) => this.setState({taxReturnPeriodYear: e.target.value})}/>
                        </label>
                        <label style={labelStyle}>
                            TaxStatePeriod quarter:
                            <select
                                value={this.state.taxReturnPeriodQuarter}
                                onChange={(e) => this.setState({taxReturnPeriodQuarter: e.target.value})}>
                                <option></option>
                                {quarterOptions}
                            </select>
                        </label>

                        <label style={labelStyle}>
                            TaxStatePeriod quarter:
                            <select
                                value={this.state.taxReturnPeriodMonth}
                                onChange={(e) => this.setState({taxReturnPeriodMonth: e.target.value})}>
                                <option></option>
                                {monthOptions}
                            </select>
                        </label>
                        <button type="submit">Upload</button>
                    </form>
                </div>
            </div>
        );
    }

}

export default UploadFormComponent