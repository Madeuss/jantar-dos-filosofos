class Jantar {
	public static void main(String[] args) throws Exception {
		final Filosofo[] filosofos = new Filosofo[5];
		final Object[] hashis = new Object[5];

		for (int i = 0; i < hashis.length; i++) {
			hashis[i] = new Object();
		}

		for (int i = 0; i < filosofos.length; i++) {
			Object hashiEsquerdo = hashis[i];
			Object hashiDireito = hashis[(i + 1) % hashis.length];

			filosofos[i] = new Filosofo(hashiEsquerdo, hashiDireito);

			Thread thread = new Thread(filosofos[i], "Filósofo " + (i + 1));
			thread.start();
		}
	}
}

class Filosofo extends Thread {
	private Object hashiEsquerdo;
	private Object hashiDireito;

	public Filosofo(Object hashiEsquerdo, Object hashiDireito) {
		this.hashiEsquerdo = hashiEsquerdo;
		this.hashiDireito = hashiDireito;
	}

	private void doAction(String action) throws InterruptedException {
		System.out.println(Thread.currentThread().getName() + " " + action);
		Thread.sleep(((int) (Math.random() * 100)));
	}

	@Override
	public void run() {
		try {
			while (true) {
				doAction("Pensando...");
				synchronized (hashiEsquerdo) {
					doAction("Pegou o hashi esquerdo");
					synchronized (hashiDireito) {
						doAction("Pegou o hashi direito - comendo :yum:");
						doAction("Soltou o hashi direito");
					}
					doAction("Soltou o hashi esquerdo");
				}
			}
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			return;
		}
	}
}
