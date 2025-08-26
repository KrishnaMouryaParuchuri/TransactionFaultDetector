document.getElementById('transactionForm').addEventListener('submit', function(event) {
    event.preventDefault();
    const userId = document.getElementById('userId').value;
    const amount = parseFloat(document.getElementById('amount').value);

    fetch('/api/transactions', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ userId, amount })
    }).then(response => {
        if (response.ok) {
            console.log('Transaction sent');
        }
    });
});

function fetchFaults() {
    fetch('/api/transactions/faults')
        .then(response => response.json())
        .then(faults => {
            const faultsDiv = document.getElementById('faults');
            faultsDiv.innerHTML = '';
            faults.forEach(fault => {
                const p = document.createElement('p');
                p.textContent = `User: ${fault.userId}, Transactions: ${fault.transactionCount}, Detected: ${new Date(fault.detectedAt).toLocaleString()}`;
                faultsDiv.appendChild(p);
            });
        });
}

setInterval(fetchFaults, 5000); // Poll every 5 seconds
fetchFaults(); // Initial fetch