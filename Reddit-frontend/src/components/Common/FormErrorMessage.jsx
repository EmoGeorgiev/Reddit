const FormErrorMessage = ({ children }) => {
    if (!children) {
        return <></>
    }

    return (
        <div className='my-2 text-red-500 text-center text-sm font-semibold'>
            {children}
        </div>
    )
}

export default FormErrorMessage