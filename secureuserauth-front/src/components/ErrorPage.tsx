import React, { useEffect, useState } from 'react'

interface ErrorProps {
    type: number,
    message: string,
}
function ErrorPage({type, message}: ErrorProps) {
    const [errorType] = useState<string>(() => {
        switch (type) {
            case 401:
                return "Unauthorized"
        
            case 403:
                return "Forbidden"
        
            case 404:
                return "Not Found"
                
            default:
                return "Unknown Error"
        } 

    });


  return (
    <div className='content'>
        <h3>Error {type}: {errorType}</h3>

        <p>{message}</p>
    </div>
  )
}

export default ErrorPage