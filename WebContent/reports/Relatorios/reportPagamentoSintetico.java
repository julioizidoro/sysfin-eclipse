<?xml version="1.0" encoding="UTF-8"?>
<jasperReport xmlns="http://jasperreports.sourceforge.net/jasperreports" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://jasperreports.sourceforge.net/jasperreports http://jasperreports.sourceforge.net/xsd/jasperreport.xsd" name="Relatório de Pagamentos" pageWidth="595" pageHeight="842" columnWidth="555" leftMargin="20" rightMargin="20" topMargin="20" bottomMargin="20" uuid="feecb075-5a7f-42f6-832b-1a654a7aa009">
	<property name="ireport.zoom" value="1.0"/>
	<property name="ireport.x" value="0"/>
	<property name="ireport.y" value="0"/>
	<style name="Title" fontName="Arial" fontSize="26" isBold="true" pdfFontName="Helvetica-Bold"/>
	<style name="SubTitle" forecolor="#666666" fontName="Arial" fontSize="18"/>
	<style name="Column header" forecolor="#666666" fontName="Arial" fontSize="12" isBold="true"/>
	<style name="Detail" fontName="Arial" fontSize="12"/>
	<parameter name="periodo" class="java.lang.String"/>
	<parameter name="logo" class="java.awt.Image"/>
	<parameter name="sql" class="java.lang.String">
		<defaultValueExpression><![CDATA[]]></defaultValueExpression>
	</parameter>
	<queryString>
		<![CDATA[$P!{sql}]]>
	</queryString>
	<field name="dataCompensacao" class="java.sql.Date">
		<fieldDescription><![CDATA[]]></fieldDescription>
	</field>
	<field name="descricao" class="java.lang.String">
		<fieldDescription><![CDATA[]]></fieldDescription>
	</field>
	<field name="valorEntrada" class="java.lang.Float">
		<fieldDescription><![CDATA[]]></fieldDescription>
	</field>
	<field name="valorSaida" class="java.lang.Float">
		<fieldDescription><![CDATA[]]></fieldDescription>
	</field>
	<field name="nome" class="java.lang.String">
		<fieldDescription><![CDATA[]]></fieldDescription>
	</field>
	<field name="nomeFantasia" class="java.lang.String">
		<fieldDescription><![CDATA[]]></fieldDescription>
	</field>
	<field name="planoContas" class="java.lang.String"/>
	<field name="idPlanoContas" class="java.lang.Integer"/>
	<field name="compentencia" class="java.lang.String">
		<fieldDescription><![CDATA[]]></fieldDescription>
	</field>
	<variable name="pTotalEntrada" class="java.lang.Float" resetType="Group" resetGroup="PlanoContas" calculation="Sum">
		<variableExpression><![CDATA[$F{valorEntrada}]]></variableExpression>
	</variable>
	<variable name="pTotalSaida" class="java.lang.Float" resetType="Group" resetGroup="PlanoContas" calculation="Sum">
		<variableExpression><![CDATA[$F{valorSaida}]]></variableExpression>
	</variable>
	<variable name="totalSaida" class="java.lang.Float" calculation="Sum">
		<variableExpression><![CDATA[$F{valorSaida}]]></variableExpression>
	</variable>
	<variable name="totalEntrada" class="java.lang.Float" calculation="Sum">
		<variableExpression><![CDATA[$F{valorEntrada}]]></variableExpression>
	</variable>
	<group name="PlanoContas" isReprintHeaderOnEachPage="true">
		<groupExpression><![CDATA[$F{idPlanoContas}]]></groupExpression>
		<groupHeader>
			<band height="32">
				<staticText>
					<reportElement x="439" y="6" width="68" height="14" forecolor="#666666" uuid="f80638d3-8726-4c31-a593-89ae0faac252"/>
					<textElement>
						<font isBold="true"/>
					</textElement>
					<text><![CDATA[Pagamentos]]></text>
				</staticText>
				<staticText>
					<reportElement x="8" y="6" width="95" height="14" forecolor="#666666" uuid="22c04091-2c6a-467f-bad6-f06bde6c78f3"/>
					<textElement>
						<font isBold="true"/>
					</textElement>
					<text><![CDATA[Plano de contas]]></text>
				</staticText>
				<line>
					<reportElement x="1" y="23" width="554" height="1" uuid="a2660627-9a9e-4d29-906d-eb2b90bf545f"/>
				</line>
			</band>
		</groupHeader>
		<groupFooter>
			<band height="3"/>
		</groupFooter>
	</group>
	<background>
		<band splitType="Stretch"/>
	</background>
	<title>
		<band height="197" splitType="Stretch">
			<image>
				<reportElement x="8" y="6" width="255" height="94" uuid="40fae967-115c-4cd6-8e9d-2e8b709725db"/>
				<imageExpression><![CDATA[$P{logo}]]></imageExpression>
			</image>
			<textField pattern="dd/MM/yyyy">
				<reportElement x="89" y="149" width="246" height="20" uuid="97f66d0c-62c8-4c04-b2db-46022e48e104"/>
				<textElement>
					<font size="12"/>
				</textElement>
				<textFieldExpression><![CDATA[$F{nomeFantasia}]]></textFieldExpression>
			</textField>
			<textField>
				<reportElement style="Detail" x="1" y="106" width="553" height="33" uuid="7ab278a3-567e-4655-aabb-0e815544389f"/>
				<textElement textAlignment="Center">
					<font size="26" isBold="true"/>
				</textElement>
				<textFieldExpression><![CDATA["Pagamentos Sintético"]]></textFieldExpression>
			</textField>
			<textField>
				<reportElement x="13" y="149" width="71" height="20" uuid="13cfd098-24aa-4c01-af9f-397e884c05f6"/>
				<textElement>
					<font size="12"/>
				</textElement>
				<textFieldExpression><![CDATA["Unidade : "]]></textFieldExpression>
			</textField>
			<textField pattern="" isBlankWhenNull="true">
				<reportElement x="13" y="176" width="473" height="20" uuid="893c68dc-007c-4929-819c-088093de6ff8"/>
				<textElement>
					<font size="12"/>
				</textElement>
				<textFieldExpression><![CDATA[$P{periodo}]]></textFieldExpression>
			</textField>
		</band>
	</title>
	<pageHeader>
		<band splitType="Stretch"/>
	</pageHeader>
	<detail>
		<band height="17" splitType="Stretch">
			<textField isBlankWhenNull="true">
				<reportElement x="8" y="2" width="255" height="13" uuid="ee42823d-1d75-4624-b06d-c8e5a0bf95bd"/>
				<textFieldExpression><![CDATA[$F{descricao}]]></textFieldExpression>
			</textField>
			<textField pattern="#,##0.00" isBlankWhenNull="true">
				<reportElement x="312" y="2" width="234" height="13" uuid="404d5bc9-58e8-4b64-ae8b-64c4693e75ca"/>
				<textElement textAlignment="Right"/>
				<textFieldExpression><![CDATA[$V{totalEntrada}+$V{totalSaida}]]></textFieldExpression>
			</textField>
		</band>
	</detail>
	<pageFooter>
		<band height="37" splitType="Stretch">
			<textField pattern="EEEEE dd MMMMM yyyy">
				<reportElement x="4" y="20" width="127" height="14" uuid="7ae4e633-e737-4a5a-acbf-bd9d128532ca"/>
				<textFieldExpression><![CDATA[new java.util.Date()]]></textFieldExpression>
			</textField>
			<textField>
				<reportElement x="417" y="20" width="80" height="14" uuid="d6a8b6e3-019d-48d8-ab61-09b61af8dfe3"/>
				<textElement textAlignment="Right"/>
				<textFieldExpression><![CDATA["Pagina "+$V{PAGE_NUMBER}+" de"]]></textFieldExpression>
			</textField>
			<textField evaluationTime="Report">
				<reportElement x="507" y="20" width="40" height="14" uuid="cae9b9ca-2d3d-468c-9660-ef67fa87025c"/>
				<textFieldExpression><![CDATA[" " + $V{PAGE_NUMBER}]]></textFieldExpression>
			</textField>
		</band>
	</pageFooter>
</jasperReport>
