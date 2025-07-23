const FormErrorMessage = ({ children }) => {
    if (!children) {
        return <></>
    }

    return (
        <div className='text-red-500 text-center'>
            {children}
        </div>
    )
}

export default FormErrorMessage